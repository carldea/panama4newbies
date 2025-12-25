#!/bin/bash
# Assumptions:
#   1. JAVA_HOME if not set the default home will be SDKMan's Current Java installed.
#   2. JAVA_HOME is pointing to a directory of a JDK
#   3. JEXTRACT_HOME is pointing to a directory of Jextract. Download jextract from https://jdk.java.net/jextract/
#                    here I use: Build 25-jextract+2-4 (2025/11/25).
#   4. Major JDK version must be the same as JExtract. e.g. JDK 25, Jextract should be 25
#   5. If caller of this file is for unix/linux shell. If you are on Windows use cygwin or similar.
export JAVA_HOME=${JAVA_HOME:-$HOME/.sdkman/candidates/java/current}
export JEXTRACT_HOME=${JEXTRACT_HOME:-$HOME/projects/panama_experiments/jextract-25}
export PATH=$JAVA_HOME/bin:$JEXTRACT_HOME/bin:$PATH
