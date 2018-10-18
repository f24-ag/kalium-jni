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

package org.libsodium.jni.crypto;

import org.libsodium.jni.Sodium;
import org.libsodium.jni.NaCl;

import org.junit.Assert;
import org.junit.Test;
import org.libsodium.jni.fixture.TestVectors;

import java.util.Arrays;

import static org.libsodium.jni.fixture.TestVectors.SHA256_MESSAGE;
import static junit.framework.Assert.assertTrue;
import static org.libsodium.jni.encoders.Encoder.HEX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HashTest {

    /**
     * Blake2 test vectors
     */

    public static final String Blake2_Message="Hello world";
    public static final String Blake2_HEXDIGEST="6ff843ba685842aa82031d3f53c48b66326df7639a63d128974c5c14f31a0f33343a8c65551134ed1ae0f2b0dd2bb495dc81039e3eeb0aa1bb0388bbeac29183";

    public static final String Blake2_KEY = "This is a super secret key. Ssshh!";
    public static final String Blake2_SALT = "0123456789abcdef";
    public static final String Blake2_PERSONAL = "fedcba9876543210";
    public static final String Blake2_DIGEST_WITH_SALT_PERSONAL = "108e81d0c7b0487de45c54554ea35b427f886b098d792497c6a803bbac7a5f7c";

    public static final String HASH_ERR = "Hash is invalid";

    private final Hash hash = new Hash();

    @Test
    public void testGenericHashInit() {
        Sodium sodium= NaCl.sodium();
        byte[] state = new byte[Sodium.crypto_generichash_statebytes()];
        byte[] hash = new byte[Sodium.crypto_generichash_bytes()];
        byte[] key = new byte[Sodium.crypto_generichash_keybytes()];
        Sodium.randombytes(key, key.length);
        if (0 == Sodium.crypto_generichash_init(state, key, key.length, hash.length)) {
                System.out.println("init success");
        } else {
                System.out.println("init failed");
        }
    }

    @Test
    public void testSha256() throws Exception {
        final byte[] rawMessage = TestVectors.SHA256_MESSAGE.getBytes();
        String result = HEX.encode(hash.sha256(rawMessage));
        assertTrue("Hash is invalid", Arrays.equals(TestVectors.SHA256_DIGEST.getBytes(), result.getBytes()));
    }

    @Test
    public void testSha256EmptyString() throws Exception {
        byte[] result = hash.sha256("".getBytes());
        assertEquals("Hash is invalid", TestVectors.SHA256_DIGEST_EMPTY_STRING, HEX.encode(result));
    }

    @Test
    public void testSha256HexString() throws Exception {
        String result = hash.sha256(TestVectors.SHA256_MESSAGE, HEX);
        Assert.assertEquals("Hash is invalid", TestVectors.SHA256_DIGEST, result);
    }

    @Test
    public void testSha256EmptyHexString() throws Exception {
        String result = hash.sha256("", HEX);
        Assert.assertEquals("Hash is invalid", TestVectors.SHA256_DIGEST_EMPTY_STRING, result);
    }

    @Test
    public void testSha256NullByte() {
        try {
            hash.sha256("\0".getBytes());
        } catch (Exception e) {
            fail("Should not raise any exception on null byte");
        }
    }

    @Test
    public void testPbkdf2Sha256() {
        try {
            byte[] salt = new Random().randomBytes();
            String key = hash.pbkdf2_sha256(SHA256_MESSAGE, HEX, salt, 100);
            System.err.println(key);
        } catch (Exception e) {
            fail("Should not raise any exception on null byte");
        }
    }
    
    @Test
    public void testSha512() throws Exception {
        final byte[] rawMessage = TestVectors.SHA512_MESSAGE.getBytes();
        String result = HEX.encode(hash.sha512(rawMessage));
        assertTrue("Hash is invalid", Arrays.equals(TestVectors.SHA512_DIGEST.getBytes(), result.getBytes()));
    }

    @Test
    public void testSha512EmptyString() throws Exception {
        byte[] result = hash.sha512("".getBytes());
        assertEquals("Hash is invalid", TestVectors.SHA512_DIGEST_EMPTY_STRING, HEX.encode(result));
    }

    @Test
    public void testSha512HexString() throws Exception {
        String result = hash.sha512(TestVectors.SHA512_MESSAGE, HEX);
        Assert.assertEquals("Hash is invalid", TestVectors.SHA512_DIGEST, result);
    }

    @Test
    public void testSha512EmptyHexString() throws Exception {
        String result = hash.sha512("", HEX);
        Assert.assertEquals("Hash is invalid", TestVectors.SHA512_DIGEST_EMPTY_STRING, result);
    }

    @Test
    public void testSha512NullByte() {
        try {
            hash.sha512("\0".getBytes());
        } catch (Exception e) {
            fail("Should not raise any exception on null byte");
        }
    }

    
}
