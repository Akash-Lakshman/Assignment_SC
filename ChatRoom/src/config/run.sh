#!/bin/bash
#This script will run the java server for multi threaded chat server
echo Started Execution....
THIS_IP=10.62.0.37
PORT=$1
echo Successfully starting the Server for the Multi Thread Chat Room on IP : $THIS_IP 
echo PORT : $PORT
cd "../../src/"
java com.SCassignment.chatserver.Server $PORT $THIS_IP


