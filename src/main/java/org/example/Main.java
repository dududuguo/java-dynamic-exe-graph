/*
 * MIT License
 *
 * Copyright (c) 2024 zhihao guo
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

package org.example;

import org.example.utils.StepDebug;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        String MainPath ;
        String ClassPath;
        String OutputPath = System.getProperty("user.dir");

        if(args.length == 0){
            System.out.println("Usage: java -jar dynamic-exe-graph-1.1.jar <MainPath> <ClassPath>");
            return;
        }

        if(Objects.equals(args[0], "-h") || Objects.equals(args[0], "-help")){
            System.out.println("javac <MainPath>.java -g -d <ClassPath>");
            System.out.println("Usage: java -jar dynamic-exe-graph-1.1.jar <MainPath> <ClassPath>");
            return;
        }

        if (args.length < 2 ) {
            System.out.println("No input file");
            return;
        }

        MainPath = args[0];
        ClassPath = args[1];
        StepDebug stepDebug = new StepDebug(MainPath, OutputPath, ClassPath);
        stepDebug.start();
    }
}