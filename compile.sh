#!/bin/bash
echo Started Compilation.... 
echo Compiling to start the multi threaded chat server 
cd '../../src/com/SCassignment/chatserver/'
pwd
javac -cp -sourcepath *.java
echo Compilation Successful !! The Server can now be started.
