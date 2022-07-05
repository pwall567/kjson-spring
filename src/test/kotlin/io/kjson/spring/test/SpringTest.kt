/*
 * @(#) SpringTest.kt
 *
 * kjson-spring  Spring JSON message converter for kjson
 * Copyright (c) 2022 Peter Wall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.kjson.spring.test

import kotlin.test.Test

import java.time.LocalDate
import java.util.UUID

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

import io.kjson.spring.test.JSONMatcher.Companion.matchesJSON
import io.kjson.stringifyJSON

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestConfiguration::class])
@AutoConfigureMockMvc
class SpringTest {

    @Autowired lateinit var mockMvc: MockMvc

    @Test fun `should load Spring context`() {
        // do nothing
    }

    @Test fun `should use kjson for output`() {
        mockMvc.get("/testendpoint") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content {
                matchesJSON {
                    property("date", LocalDate.of(2022, 7, 1))
                    property("extra", "Hello!")
                }
            }
        }
    }

    @Test fun `should use kjson for input`() {
        mockMvc.post("/testendpoint") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"id":"0e457a9e-fb40-11ec-84d9-a324b304f4f9","name":"Me"}"""
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content {
                matchesJSON {
                    property("date", LocalDate.of(2022, 7, 4))
                    property("extra", "0e457a9e-fb40-11ec-84d9-a324b304f4f9")
                }
            }
        }
    }

}
