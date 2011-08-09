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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

/** Manages creating {@link Channel}s to receive messages from the server. */
public class ChannelFactory {

  private static final String CHANNEL_SRC = "/_ah/channel/jsapi";
  private static boolean scriptLoaded = false;

  // Channel ID being created the first time a channel is created, stored here
  // while the jsapi script is being retrieved and executed
  private static String channelId;

  private static Channel channel = null;

  public static Channel createChannel(final String channelId) {
    ChannelFactory.channelId = channelId;

    if (!scriptLoaded) {
      addJsniCallback();
      RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, CHANNEL_SRC);
      try {
        rb.sendRequest("", new RequestCallback() {
          @Override
          public void onResponseReceived(Request request, Response response) {
            String text = response.getText() + ";__gwt_ChannelFactory_callback();";
            ScriptElement script = Document.get().createScriptElement();
            script.setText(text);
            Document.get().getElementsByTagName("head").getItem(0).appendChild(script);
          }

          @Override
          public void onError(Request request, Throwable exception) {
            throw new RuntimeException(exception);
          }
        });
      } catch (RequestException e) {
        throw new RuntimeException(e);
      }
      return channel;
    } else {
      return createChannelImpl();
    }
  }

  private static final native void addJsniCallback() /*-{
    $wnd.__gwt_ChannelFactory_callback = function() {
      @com.google.gwt.appengine.channel.client.ChannelFactory::scriptLoaded = true;
      @com.google.gwt.appengine.channel.client.ChannelFactory::channel =
          @com.google.gwt.appengine.channel.client.ChannelFactory::createChannelImpl();
    };
  }-*/;

  private static final native Channel createChannelImpl() /*-{
    return new $wnd.goog.appengine.Channel(
        @com.google.gwt.appengine.channel.client.ChannelFactory::channelId);
  }-*/;
}