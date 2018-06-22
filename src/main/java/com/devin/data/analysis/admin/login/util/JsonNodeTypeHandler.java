package com.devin.data.analysis.admin.login.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*
   <columnOverride column="json_string" javaType="com.fasterxml.jackson.databind.JsonNode" typeHandler="JsonNodeTypeHandler"/>
 */
public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {
    private static final ObjectMapper mapper = new ObjectMapper();


        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
            String str = null;
            try {
                str = mapper.writeValueAsString(parameter);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                str = "{}";
            }
            ps.setString(i, str);
        }

        @Override
        public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
            String jsonSource = rs.getString(columnName);
            if(jsonSource == null){
                return null;
            }
            try {
                JsonNode jsonNode = mapper.readTree(jsonSource);
                return jsonNode;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            String jsonSource = rs.getString(columnIndex);
            if(jsonSource == null){
                return null;
            }
            try {
                JsonNode jsonNode = mapper.readTree(jsonSource);
                return jsonNode;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            String jsonSource = cs.getString(columnIndex);
            if(jsonSource == null){
                return null;
            }
            try {
                JsonNode jsonNode = mapper.readTree(jsonSource);
                return jsonNode;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    public static class JacksonUtil {
        public static String parseString(String body, String field) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = null;
            try {
                node = mapper.readTree(body);
                JsonNode leaf = node.get(field);
                if(leaf != null)
                    return leaf.asText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static Integer parseInteger(String body, String field) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = null;
            try {
                node = mapper.readTree(body);
                JsonNode leaf = node.get(field);
                if(leaf != null)
                    return leaf.asInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static List<Integer> parseIntegerList(String body, String field) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = null;
            try {
                node = mapper.readTree(body);
                JsonNode leaf = node.get(field);

                if(leaf != null)
                    return mapper.convertValue(leaf, new TypeReference<List<String>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        public static Boolean parseBoolean(String body, String field) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = null;
            try {
                node = mapper.readTree(body);
                JsonNode leaf = node.get(field);
                if(leaf != null)
                    return leaf.asBoolean();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static Short parseShort(String body, String field) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = null;
            try {
                node = mapper.readTree(body);
                JsonNode leaf = node.get(field);
                if(leaf != null) {
                    Integer value = leaf.asInt();
                    return value.shortValue();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static <T> T parseObject(String body, String field, Class<T> clazz) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = null;
            try {
                node = mapper.readTree(body);
                node = node.get(field);
                return mapper.treeToValue(node, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static Object toNode(String json) {
            if(json == null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(json);
                return jsonNode;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}