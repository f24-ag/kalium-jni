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

package org.abstractj.kalium.crypto;

import org.abstractj.kalium.encoders.Encoder;

import static org.abstractj.kalium.NaCl.sodium;
import static org.abstractj.kalium.SodiumConstants.PUBLICKEY_BYTES;
import static org.abstractj.kalium.SodiumConstants.SHA256BYTES;
import static org.abstractj.kalium.SodiumConstants.SHA512BYTES;

public class Hash {

    private static final int KEY_LEN = 64;
    private static final int SALTBYTES = 32;

    public byte[] sha256(byte[] message) {
        byte[] buffer = new byte[SHA256BYTES];
        sodium().crypto_hash_sha256(buffer, message, message.length);
        return buffer;
    }

    public byte[] sha512(byte[] message) {
        byte[] buffer = new byte[SHA512BYTES];
        sodium().crypto_hash_sha512(buffer, message, message.length);
        return buffer;
    }
    
    public String sha256(String message, Encoder encoder) {
        byte[] hash = sha256(message.getBytes());
        return encoder.encode(hash);
    }

    public String sha512(String message, Encoder encoder) {
        byte[] hash = sha512(message.getBytes());
        return encoder.encode(hash);
    }

    public String pwhash_scryptsalsa208sha256(String passwd, Encoder encoder, byte[] salt, int opslimit, long memlimit) {
        byte[] buffer = new byte[KEY_LEN];
        sodium().crypto_pwhash_scryptsalsa208sha256(buffer, buffer.length, passwd, passwd.length(), salt, opslimit, memlimit);
        return encoder.encode(buffer);
    }

    public String pbkdf2_sha256(String password, Encoder encoder, byte[] salt, int iterations) {
        byte[] buffer = new byte[PUBLICKEY_BYTES];
        byte[] password_bytes = password.getBytes();
        sodium().PBKDF2_SHA256(password_bytes, password_bytes.length, salt, salt.length, iterations, buffer, PUBLICKEY_BYTES);
        return encoder.encode(buffer);
    }

    /*
    public byte[] blake2(byte[] message) throws UnsupportedOperationException {
        if (!blakeSupportedVersion()) throw new UnsupportedOperationException();

        buffer = new byte[BLAKE2B_OUTBYTES];
        sodium().crypto_generichash_blake2b(buffer, BLAKE2B_OUTBYTES, message, message.length, null, 0);
        return buffer;
    }


    public String blake2(String message, Encoder encoder) throws UnsupportedOperationException {
        if (!blakeSupportedVersion()) throw new UnsupportedOperationException();
        byte[] hash = blake2(message.getBytes());
        return encoder.encode(hash);
    }

    private boolean blakeSupportedVersion(){
        return sodium().sodium_version_string().compareTo("0.4.0") >= 0 ;
    }
    */
}
