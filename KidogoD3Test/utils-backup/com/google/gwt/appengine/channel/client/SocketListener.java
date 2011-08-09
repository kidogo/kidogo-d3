/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.appengine.channel.client;


/** Listener to receive messages from the server. */
public interface SocketListener {
        /** Called when the channel is opened. */
        void onOpen();

        /** Called when the channel receives a message from the server. */
        void onMessage(String message);
        
        void onError(String description, int code);
        
        void onClose();
}