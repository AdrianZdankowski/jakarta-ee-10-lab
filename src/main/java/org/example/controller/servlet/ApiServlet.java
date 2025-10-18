package org.example.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.airplane.controller.api.AirplaneController;
import org.example.airplane.controller.api.PlaneTypeController;
import org.example.pilot.controller.api.PilotController;
import org.example.pilot.dto.PatchPilotRequest;
import org.example.pilot.dto.PutPilotRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {ApiServlet.Paths.API + "/*"})
@MultipartConfig(maxFileSize = 500 * 1024)
public class ApiServlet extends HttpServlet {

    private final PilotController pilotController;
    private final AirplaneController airplaneController;
    private final PlaneTypeController planeTypeController;
    private final Jsonb jsonb = JsonbBuilder.create();
    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern PILOTS = Pattern.compile("/pilots/?");
        public static final Pattern PILOT = Pattern.compile("/pilots/(%s)".formatted(UUID.pattern()));
        public static final Pattern PILOT_AVATAR = Pattern.compile("/pilots/(%s)/avatar".formatted(UUID.pattern()));
        public static final Pattern PLANETYPES = Pattern.compile("/planetypes/?");
        public static final Pattern PLANETYPE = Pattern.compile("/planetypes/(%s)".formatted(UUID.pattern()));
        public static final Pattern PLANETYPE_AIRPLANES = Pattern.compile("/planetypes/(%s)/airplanes/?".formatted(UUID.pattern()));
        public static final Pattern PILOT_AIRPLANES = Pattern.compile("/pilots/(%s)/airplanes/?".formatted(UUID.pattern()));
        public static final Pattern AIRPLANES = Pattern.compile("/airplanes/?");
        public static final Pattern AIRPLANE = Pattern.compile("/airplanes/(%s)".formatted(UUID.pattern()));
    }


    @Inject
    public ApiServlet(PilotController pilotController,
                      AirplaneController airplaneController,
                      PlaneTypeController planeTypeController) {
        this.pilotController = pilotController;
        this.airplaneController = airplaneController;
        this.planeTypeController = planeTypeController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        }
        else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();

        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PILOTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(pilotController.getPilots()));
                return;
            }
            else if (path.matches(Patterns.PILOT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PILOT, path);
                response.getWriter().write(jsonb.toJson(pilotController.getPilot(uuid)));
                return;
            }
            else if (path.matches(Patterns.PILOT_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.PILOT_AVATAR, path);
                byte[] avatar = pilotController.getPilotAvatar(uuid);
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                return;
            }
            else if (path.matches(Patterns.AIRPLANES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(airplaneController.getAirplanes()));
                return;
            }
            else if (path.matches(Patterns.AIRPLANE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.AIRPLANE, path);
                response.getWriter().write(jsonb.toJson(airplaneController.getAirplane(uuid)));
                return;
            }
            else if (path.matches(Patterns.PLANETYPES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(planeTypeController.getPlaneTypes()));
                return;
            }
            else if (path.matches(Patterns.PLANETYPE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PLANETYPE, path);
                response.getWriter().write(jsonb.toJson(planeTypeController.getPlaneType(uuid)));
                return;
            }
            else if (path.matches(Patterns.PLANETYPE_AIRPLANES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PLANETYPE_AIRPLANES, path);
                response.getWriter().write(jsonb.toJson(airplaneController.getPlaneTypeAirplanes(uuid)));
                return;
            }
            else if (path.matches(Patterns.PILOT_AIRPLANES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PILOT_AIRPLANES, path);
                response.getWriter().write(jsonb.toJson(airplaneController.getPilotAirplanes(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PILOT.pattern())) {
                UUID uuid = extractUuid(Patterns.PILOT, path);
                pilotController.putPilot(uuid, jsonb.fromJson(request.getReader(), PutPilotRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "pilots", uuid.toString()));
                return;
            }
            else if (path.matches(Patterns.PILOT_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.PILOT_AVATAR, path);
                pilotController.putPilotAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PILOT.pattern())) {
                UUID uuid = extractUuid(Patterns.PILOT, path);
                pilotController.deletePilot(uuid);
                return;
            }
            else if (path.matches(Patterns.PILOT_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.PILOT_AVATAR, path);
                pilotController.deletePilotAvatar(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PILOT.pattern())) {
                UUID uuid = extractUuid(Patterns.PILOT, path);
                pilotController.patchPilot(uuid, jsonb.fromJson(request.getReader(), PatchPilotRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }
}
