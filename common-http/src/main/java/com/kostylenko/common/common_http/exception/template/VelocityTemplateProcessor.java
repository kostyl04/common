package com.kostylenko.common.common_http.exception.template;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

import static java.util.Objects.isNull;

public class VelocityTemplateProcessor implements TemplateProcessor {

    private final VelocityEngine velocityEngine;

    public VelocityTemplateProcessor() {
        velocityEngine = new VelocityEngine();
        velocityEngine.init();
    }

    @Override
    public String process(String template, Map<String, Object> args) throws TemplateProcessingException {
        VelocityContext velocityContext = buildContext(args);
        try (StringWriter result = new StringWriter()) {
            boolean evaluate = velocityEngine.evaluate(velocityContext, result, "velocity", template);
            if (evaluate) {
                return result.toString();
            }
            throw new TemplateProcessingException("Error processing template: " + template);
        } catch (Exception e) {
            throw new TemplateProcessingException("Error processing template: " + template, e);
        }
    }

    @Override
    public String process(String template, Map<String, Object> args, String defaultValue) {
        try {
            return process(template, args);
        } catch (TemplateProcessingException e) {
            return defaultValue;
        }
    }

    private VelocityContext buildContext(Map<String, Object> args) {
        if (isNull(args)) {
            return new VelocityContext();
        }
        VelocityContext velocityContext = new VelocityContext();
        args.forEach(velocityContext::put);
        return velocityContext;
    }
}
