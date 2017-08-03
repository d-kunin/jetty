package com.example.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleHandler extends AbstractHandler {

  final String greeting;
  final String body;

  public SimpleHandler(final String greeting, final String body) {
    this.greeting = greeting;
    this.body = body;
  }

  @Override
  public void handle(final String target,
                     final Request request,
                     final HttpServletRequest httpServletRequest,
                     final HttpServletResponse httpServletResponse)
      throws IOException, ServletException
  {
    if (!"/time".equals(target)) {
      return;
    }

    httpServletResponse.setContentType("text/html; charset=utf-8");
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);

    final PrintWriter out = httpServletResponse.getWriter();
    out.println(String.format("<h1>%s</h1>", greeting));
    if (null != body) {
      out.println(body);
    }

    request.setHandled(true);
  }
}
