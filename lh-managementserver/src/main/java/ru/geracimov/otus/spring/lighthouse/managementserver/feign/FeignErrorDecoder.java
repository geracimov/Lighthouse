package ru.geracimov.otus.spring.lighthouse.managementserver.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geracimov.otus.spring.lighthouse.managementserver.feign.exception.FeignClientException;
import ru.geracimov.otus.spring.lighthouse.managementserver.feign.exception.FeignServerException;

import java.io.IOException;
import java.io.Reader;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String reason = "";

        try (Reader reader = response.body()
                                     .asReader()) {
            String reasonJson = readValue(reader);
            ExceptionMessage exceptionMessage = mapper.readValue(reasonJson,
                                                                 ExceptionMessage.class);
            reason = exceptionMessage.message;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.status() >= 400 && response.status() <= 499
                ? new FeignClientException(response.status(), reason)
                : new FeignServerException(response.status(), reason);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class ExceptionMessage {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }

    private String readValue(Reader reader) throws IOException {
        StringBuilder to = new StringBuilder();
        char[] buf = new char[2048];
        int nRead;

        while ((nRead = reader.read(buf)) != -1) {
            to.append(buf, 0, nRead);
        }
        return to.toString();
    }

}