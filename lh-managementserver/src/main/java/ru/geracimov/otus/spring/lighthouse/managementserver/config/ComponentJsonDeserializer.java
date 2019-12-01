package ru.geracimov.otus.spring.lighthouse.managementserver.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pi4j.component.Component;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ComponentJsonDeserializer<C extends Component> extends JsonDeserializer<C> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public C deserialize(JsonParser jsonParser,
                         DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        TreeNode treeNode = jsonParser.getCodec()
                                      .readTree(jsonParser);
        String cl = ((TextNode) treeNode.get("properties")
                                        .get("class")).asText();
        Class<C> clazz;
        try {
            clazz = (Class<C>) Class.forName(cl);
            return mapper.treeToValue(treeNode, clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
