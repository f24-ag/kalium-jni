#!/bin/bash -ev

jnilib=libsodiumjni.so
destlib=/usr/lib
LIBSODIUM_PATH=/home/audrius/dev/trustcase/libsodium
if uname -a | grep -q -i darwin; then
  jnilib=libsodiumjni.jnilib
  destlib=/Library/Java/Extensions
  if [ ! -d $destlib ]; then
      sudo mkdir $destlib
  fi
else
  sudo ldconfig
fi
echo $jnilib
echo $destlib
echo $destlib/$jnilib 

#sudo cp /usr/local/lib/libsodium.* /usr/lib

gcc -I${LIBSODIUM_PATH}/src/libsodium/include -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -I${JAVA_HOME}/include/darwin sodium_wrap.c -shared -fPIC -L/usr/local/lib -L/usr/lib -lm ${LIBSODIUM_PATH}/src/libsodium/.libs/libsodium.a -o $jnilib
sudo rm -f $destlib/$jnilib
sudo cp $jnilib $destlib

