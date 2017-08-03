package com.example.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class SimpleServer {

  private final static String SCRIPT = "<script>\n" +
      "function startTime() {\n" +
      "    var today = new Date();\n" +
      "    var h = today.getHours();\n" +
      "    var m = today.getMinutes();\n" +
      "    var s = today.getSeconds();\n" +
      "    m = checkTime(m);\n" +
      "    s = checkTime(s);\n" +
      "    document.getElementById('txt').innerHTML =\n" +
      "    h + \":\" + m + \":\" + s;\n" +
      "    var t = setTimeout(startTime, 500);\n" +
      "}\n" +
      "function checkTime(i) {\n" +
      "    if (i < 10) {i = \"0\" + i};  // add zero in front of numbers < 10\n" +
      "    return i;\n" +
      "}\n" +
      "</script>" +
      "<body onload=\"startTime()\">" +
      "<div id=\"txt\"></div>\n" +
      "</body>";

  public static void main(String[] args) throws Exception {
    final Server server = new Server(8090);

    final SimpleHandler simpleHandler = new SimpleHandler("Dima", SCRIPT);

    final ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(true);
    resourceHandler.setWelcomeFiles(new String[]{"index.html"});
    resourceHandler.setResourceBase("static");

    final ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(SimpleServlet.class, "/servlet");

    final HandlerList handlerList = new HandlerList();
    handlerList.setHandlers(new Handler[]{
        resourceHandler,
        simpleHandler,
        servletHandler,
        new DefaultHandler()
    });
    server.setHandler(handlerList);

    server.start();
    server.dumpStdErr();
    server.join();
  }

}
