/*
 * The MIT License
 *
 * Copyright 2013-2014 Czech Technical University in Prague.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cz.jirutka.rsql.parser.ast

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RSQLNodesFactoryTest extends Specification {

    def factory = new RSQLNodesFactory()

    def 'create #className for logical operator: #operator'() {
        when:
            def actual = factory.createLogicalNode(operator, [])
        then:
            actual.class == expected
        where:
            operator       | expected
            LogicalOp.AND  | AndNode
            LogicalOp.OR   | OrNode

            className = expected.simpleName
    }

    def 'create #className for comparison operator: #operator'() {
        when:
            def actual = factory.createComparisonNode(operator, 'sel', ['arg0'])
        then:
            actual.class == expected
        where:
            operator | expected
            '=='     | EqualNode
            '!='     | NotEqualNode
            '=gt='   | GreaterThanNode
            '>'      | GreaterThanNode
            '=ge='   | GreaterThanOrEqualNode
            '>='     | GreaterThanOrEqualNode
            '=lt='   | LessThanNode
            '<'      | LessThanNode
            '<='     | LessThanOrEqualNode
            '=le='   | LessThanOrEqualNode
            '=in='   | InNode
            '=out='  | NotInNode

            className = expected.simpleName
    }

    def 'throw IllegalArgumentException when given unknown comparison operator'() {
        when:
            factory.createComparisonNode('=foo=', 'sel', ['arg'])
        then:
            thrown IllegalArgumentException
    }
}