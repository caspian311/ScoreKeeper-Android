#!/bin/bash

ANDROID_SDK_PATH=~/.apps/android-sdk-linux_86/tools

echo Cleaning out the old build
pushd ScoreKeeper
ant clean
popd

pushd ScoreKeeper-androidtest
ant clean
popd

echo Finding the emulator
device=`$ANDROID_SDK_PATH/android list avd | grep Name | awk '{print $2}' | head -1`
echo ...using $device

echo Starting emulator: $device
$ANDROID_SDK_PATH/emulator -noskin -avd $device &
emulator_process_id=$!
echo Emulator process id: $emulator_process_id

echo Waiting for device to start...
$ANDROID_SDK_PATH/adb wait-for-device

echo Building application
pushd ./ScoreKeeper
ant install
popd

echo Building test application
pushd ./ScoreKeeper-androidtest
ant install
popd

echo Device is up, running tests
$ANDROID_SDK_PATH/adb shell am instrument -w net.todd.scorekeeper.test/android.test.InstrumentationTestRunner

echo Removing test application from device
$ANDROID_SDK_PATH/adb uninstall net.todd.scorekeeper.test

echo Removing application from device
$ANDROID_SDK_PATH/adb uninstall net.todd.scorekeeper

echo Killing off emulator...
kill $emulator_process_id
