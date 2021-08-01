package org.simplelibrary.view;

import org.springframework.web.servlet.view.InternalResourceView;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Credit to andyb https://stackoverflow.com/a/10027097/11604596
// As well as to the original authors of the overridden method
public class JstlView extends InternalResourceView {

  @Override
  protected void renderMergedOutputModel(
      Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    // Expose the model object as request attributes.
    exposeModelAsRequestAttributes(model, request);

    // Expose helpers as request attributes, if any.
    exposeHelpers(request);

    // Determine the path for the request dispatcher.
    String dispatcherPath = prepareForRendering(request, response);
    dispatcherPath = dispatcherPath.replaceAll(".*views/", "");

    // Set original view being asked for as a request parameter.
    request.setAttribute("content", dispatcherPath);

    // Obtain a RequestDispatcher for the target resource (typically a JSP).
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/template.jsp");
    rd.include(request, response);
  }

}
