/*
 * @(#) SpringTestCustom.kt
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

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestConfigurationCustom::class])
@AutoConfigureMockMvc
class SpringCustomTest {

    @Autowired lateinit var mockMvc: MockMvc

    @Test fun `should use kjson for output with custom toJSON`() {
        mockMvc.get("/testendpoint") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content {
                string("""{"D":"2022-07-01","X":"Hello!"}""")
            }
        }
    }

    @Test fun `should use kjson for input with custom fromJSON`() {
        mockMvc.post("/testendpoint") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"I":"0e457a9e-fb40-11ec-84d9-a324b304f4f9","N":"Me"}"""
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content {
                string("""{"D":"2022-07-04","X":"0e457a9e-fb40-11ec-84d9-a324b304f4f9"}""")
            }
        }
    }

}
