#!/bin/sh

#
# Copyright (C) 2011-2017 Advanced Card Systems Ltd. All Rights Reserved.
#
# This software is the confidential and proprietary information of Advanced
# Card Systems Ltd. ("Confidential Information").  You shall not disclose such
# Confidential Information and shall use it only in accordance with the terms
# of the license agreement you entered into with ACS.
#

#
# uninstall.sh
# This script is to uninstall the library.
#

USER=`whoami`

if [ $USER != root ]; then
    echo "You must be root to run this script."
    exit 1
fi

echo "Uninstalling libctacs..."

rm -rfv /usr/include/ctacs
rm -fv /usr/lib/libctacs*
rm -fv /usr/lib/pkgconfig/libctacs.pc

echo "Completed!"
