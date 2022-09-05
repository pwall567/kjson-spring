/*
 * @(#) JSONSpring.kt
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

package io.kjson.spring

import java.io.Reader
import java.io.Writer
import java.lang.reflect.Type

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter
import org.springframework.stereotype.Service

import io.kjson.JSONConfig
import io.kjson.JSONException
import io.kjson.JSONStringify.appendJSON
import io.kjson.fromJSONValue
import io.kjson.parser.Parser

/**
 * Spring message converter to convert messages to and from JSON using the [kjson](https://github.com/pwall567/kjson)
 * library.
 *
 * @author  Peter Wall
 */
@Service
@Suppress("unused")
class JSONSpring(
    @Autowired(required = false) config: JSONConfig?,
) : AbstractJsonHttpMessageConverter() {

    private val config: JSONConfig = config ?: JSONConfig.defaultConfig

    override fun readInternal(resolvedType: Type, reader: Reader): Any {
        return Parser.parse(reader.readText(), config.parseOptions)?.fromJSONValue(resolvedType, config) ?:
                throw JSONException("Message may not be \"null\"")
    }

    override fun writeInternal(o: Any, type: Type?, writer: Writer) {
        writer.appendJSON(o, config)
    }

}
