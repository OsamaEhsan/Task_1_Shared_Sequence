/*
 * Copyright (C) 2015 Julio Vilmar Gesser and Mike DeHaan
 *
 * This file is part of antlr-java-parser.
 *
 * antlr-java-parser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * antlr-java-parser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with antlr-java-parser.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.github.antlrjavaparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;

import java.io.InputStream;


public class Main {


    public static void main(String args[]) throws Exception {
        InputStream in = Main.class.getClassLoader().getResourceAsStream("ComplexTest.java");

        if (in == null) {
            System.err.println("Unable to find test file.");
            return;
        }



        Java7Lexer lex = new Java7Lexer(new ANTLRInputStream(in));

        Token token = null;
        while ((token = lex.nextToken()) != null) {

            if (token.getType() == Token.EOF) {
                break;
            }

            if (token.getChannel() == Token.HIDDEN_CHANNEL) {
                continue;
            }

            System.out.println(token.getText());
        }

        lex.reset();
    }


}
