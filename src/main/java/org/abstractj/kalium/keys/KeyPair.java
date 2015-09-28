/**
 * Copyright 2013 Bruno Oliveira, and individual contributors
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.abstractj.kalium.keys;

import org.abstractj.kalium.crypto.Point;
import org.abstractj.kalium.encoders.Encoder;

import static org.abstractj.kalium.NaCl.sodium;
import static org.abstractj.kalium.SodiumConstants.PUBLICKEY_BYTES;
import static org.abstractj.kalium.SodiumConstants.SECRETKEY_BYTES;
import static org.abstractj.kalium.crypto.Util.checkLength;
import static org.abstractj.kalium.crypto.Util.zeros;

public class KeyPair {

    private byte[] publicKey;
    private final byte[] secretKey;

    public KeyPair() {
        this.secretKey = zeros(SECRETKEY_BYTES);
        this.publicKey = zeros(PUBLICKEY_BYTES);
        sodium().crypto_box_curve25519xsalsa20poly1305_keypair(publicKey, secretKey);
    }

    public KeyPair(byte[] secretKey) {
        this.secretKey = secretKey;
        checkLength(this.secretKey, SECRETKEY_BYTES);
    }

    public KeyPair(byte[] secretKey, byte[] publicKey) {
        this.secretKey = secretKey;
        checkLength(this.secretKey, SECRETKEY_BYTES);
        this.publicKey = publicKey;
        checkLength(this.publicKey, PUBLICKEY_BYTES);
    }

    public KeyPair(String secretKey, Encoder encoder) {
        this(encoder.decode(secretKey));
    }

    public KeyPair(String secretKey, String publicKey, Encoder encoder) {
        this(encoder.decode(secretKey), encoder.decode(publicKey));
    }

    public PublicKey getPublicKey() {
        Point point = new Point();
        byte[] key = publicKey != null ? publicKey : point.mult(secretKey).toBytes();
        return new PublicKey(key);
    }

    public PrivateKey getPrivateKey() {
        return new PrivateKey(secretKey);
    }
}
