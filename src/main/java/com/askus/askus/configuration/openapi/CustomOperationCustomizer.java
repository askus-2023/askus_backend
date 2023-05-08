package com.askus.askus.configuration.openapi;

import java.util.Set;

import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.askus.askus.global.error.ErrorResponse;
import com.askus.askus.global.openapi.ErrorResponseApi;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

/**
 * Custom API exception response Customizer
 *
 * delete default exception responses(400, 404, 500)
 * response messages defined in ErrorResponseWithMessages by ErrorResponse
 */
@Component
public class CustomOperationCustomizer implements OperationCustomizer {

	private final String mediaType;
	private String errorResponseRef;

	public CustomOperationCustomizer(SpringDocConfigProperties properties) {
		this.mediaType = properties.getDefaultProducesMediaType();
	}

	@Override
	public Operation customize(Operation operation, HandlerMethod handlerMethod) {
		ApiResponses apiResponses = operation.getResponses();
		// 1. extract references for errorResponse schema
		if (errorResponseRef == null) {
			errorResponseRef = apiResponses.get("400").getContent().get(mediaType).getSchema().get$ref();
		}

		// 2. remove default error responses
		apiResponses.remove("400");
		apiResponses.remove("404");
		apiResponses.remove("500");

		// 3. add example error responses in customized way
		Set<ErrorResponseApi> errorResponseApis = AnnotatedElementUtils.findMergedRepeatableAnnotations(
			handlerMethod.getMethod(), ErrorResponseApi.class);
		errorResponseApis.forEach(errorResponseApi -> addErrorResponseApi(apiResponses, errorResponseApi));

		return operation;
	}

	private void addErrorResponseApi(ApiResponses apiResponses, ErrorResponseApi errorResponseApi) {
		// FIXME
		ErrorResponse errorResponse = null;

		@SuppressWarnings("unchecked")
		Schema<ErrorResponse> schema = new Schema<ErrorResponse>().$ref(errorResponseRef);
		MediaType mediaTypeItem = new MediaType().schema(schema).example(errorResponse);
		Content content = new Content().addMediaType(mediaType, mediaTypeItem);
		ApiResponse apiResponse = new ApiResponse().content(content);

		apiResponses.put(String.valueOf(errorResponse.getCode()), apiResponse);
	}
}
