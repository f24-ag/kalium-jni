/**
 * Copyright 2013 Bruno Oliveira, and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.abstractj.kalium;


public class NaCl {

    public static Sodium sodium() {
        return SingletonHolder.SODIUM_INSTANCE;
    }
    
    private static final String LIBRARY_NAME = "kalium-jni";
    
    private static final class SingletonHolder {
        public static final Sodium SODIUM_INSTANCE = new Sodium();
    }
    
    private NaCl() {
    }

    static {
        String architecture = System.getProperty("os.arch");
        String operatingSystem = System.getProperty("os.name");
        boolean is64bit = false;
        if (operatingSystem.contains("Windows") || operatingSystem.contains("Linux")) {
            is64bit = architecture.equals("amd64");
        }
        String libraryName = LIBRARY_NAME;
        if (is64bit) {
            libraryName += "64";
        }
        System.loadLibrary(libraryName);
    }
}