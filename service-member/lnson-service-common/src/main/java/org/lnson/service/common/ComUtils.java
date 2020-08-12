package org.lnson.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.lnson.service.component.SpringContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ComUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = SpringContextUtils.getBean(ObjectMapper.class);
    }

    /**
     * 打印详细错误信息
     */
    public static String printException(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 日期相加/相减若干秒得到新的日期
     */
    public static long dateAddSeconds(Date dateObj, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTimeInMillis();
    }

    /**
     * 校验字符串是否为数字
     */
    public static boolean validNumber(String moneyValue) {
        String regex = "^[-+]?\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(moneyValue);
        return !matcher.matches();
    }

    /**
     * 判断字符串是否为null或""
     */
    public static boolean isNotEmpty(String value) {
        if (null == value) {
            return false;
        }
        //替换掉转码后的unicode控制字符(这些看不到的空字符无法使用trim移除,每个控制字符的length是1)
        //参考 https://blog.csdn.net/liquantong/article/details/85318595
        value = value.replaceAll("\\u202E", "");//开始从右到左的文字
        value = value.replaceAll("\\u202D", "");//开始从左到右的文字
        value = value.replaceAll("\\u202C", "");//结束上一次定义
        return !("".equals(value.trim()) || "null".equalsIgnoreCase(value.trim()));
    }

    /**
     * 删除字符串source开头的指定字符prefix
     */
    public static String trimStart(String source, String prefix) {
        return source.replaceAll("^" + prefix, "");
    }

    /**
     * 删除字符串source结尾的指定字符suffix
     */
    public static String trimEnd(String source, String suffix) {
        return source.replaceAll(suffix + "$", "");
    }

    /**
     * 删除字符串source首尾的指定字符chr
     */
    public static String trim(String source, String chr) {
        return source.replaceAll("(^" + chr + ")|(" + chr + "$)", "");
    }

    /**
     * 字符串source是否以prefix开头
     */
    public static boolean startWith(String source, String prefix) {
        Pattern compile = Pattern.compile("^" + prefix, Pattern.CASE_INSENSITIVE);
        return compile.matcher(source).find();
    }

    /**
     * 字符串source是否以prefix开头
     */
    public static boolean endWith(String source, String suffix) {
        Pattern compile = Pattern.compile(suffix + "$", Pattern.CASE_INSENSITIVE);
        return compile.matcher(source).find();
    }

    /**
     * Jackson解析Object对象，获取字段值
     */
    public static String readObject(Object object, String fieldName) {
        if (null == object) {
            return "";
        }
        JsonNode rootNode = objectMapper.valueToTree(object);
        if (null == rootNode) {
            return "";
        }
        JsonNode jsonNode = rootNode.get(fieldName);
        if (null == jsonNode) {
            return "";
        }
        return jsonNode.size() == 0 ? jsonNode.asText() : JsonUtils.to(jsonNode);
    }

    /**
     * Jackson解析Json字符串，判断指定字段是否为数组类型，如果是则返回数组的长度
     */
    public static Integer validJsonArray(String jsonStr, String fieldKey) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonStr);
        if (rootNode == null) {
            return 0;
        }
        JsonNode fieldNode = rootNode.get(fieldKey);
        if (fieldNode == null) {
            return 0;
        }
        if (JsonNodeType.ARRAY != fieldNode.getNodeType()) {
            return 0;
        }
        return fieldNode.size();
    }

    /**
     * 更新Json的值
     */
    public static String alterJson(String sourceJson, Map<String, Object> targetMap) throws IOException {
        ObjectNode rootObjectNode = (ObjectNode) objectMapper.readTree(sourceJson);
        targetMap.forEach((key, value) -> rootObjectNode.put(key, String.valueOf(value)));
        return objectMapper.writeValueAsString(rootObjectNode);
    }

    /**
     * 判断节点是否为字符串节点且不能为空
     */
    public static Boolean isEmptyJsonNode(JsonNode node) {
        return node == null || node.getNodeType() != JsonNodeType.STRING || !ComUtils.isNotEmpty(node.asText());
    }

    /**
     * 将JSON字符串转换为JsonNode
     */
    public static JsonNode readJsonString(String jsonStr) {
        try {
            return objectMapper.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            log.error(printException(e));
            return NullNode.getInstance();
        }
    }

    /**
     * 根据路径解析Json节点
     *
     * @param rootNode    JSON根节点
     * @param fullPathKey 节点路径
     * @return 返回最后的节点
     */
    public static JsonNode readPathNode(ObjectNode rootNode, String fullPathKey) {
        if (rootNode == null || rootNode.getNodeType() == JsonNodeType.ARRAY) {
            return null;
        }
        try {
            String[] pathKeyList = fullPathKey.split("\\.");
            JsonNode jsonNode = null;
            String pathKey;
            for (int i = 0; i < pathKeyList.length; i++) {
                pathKey = pathKeyList[i];
                if (jsonNode != null && jsonNode.getNodeType() == JsonNodeType.ARRAY) {
                    break;
                }
                jsonNode = i == 0
                        ? rootNode.get(pathKey)
                        : jsonNode != null
                        ? jsonNode.get(pathKey)
                        : null;
                if (i > 0 && jsonNode == null) {
                    break;
                }
            }
            return jsonNode;
        } catch (Exception ex) {
            log.error("readPathNode解析JSON异常：" + printException(ex));
            return null;
        }
    }

    /**
     * 读取文本节点的值
     */
    public static String readNode(JsonNode node, String key) {
        JsonNode keyNode = node.get(key);
        if (keyNode == null) {
            return "";
        }
        return Arrays.asList(JsonNodeType.STRING, JsonNodeType.NUMBER, JsonNodeType.BOOLEAN, JsonNodeType.NULL).contains(keyNode.getNodeType())
                ? keyNode.asText()
                : "";
    }

    /**
     * 返回对象的字符串格式
     */
    public static String str(Object value) {
        return value instanceof String ? String.valueOf(value) : JsonUtils.to(value);
    }

}
/*
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(7);

        System.out.println("开始时间：" + startTime.format(df) + "，结束时间：" + endTime.format(df));

        LocalDateTime dateTest = LocalDateTime.parse("2018-02-30 12:12:12", df);
        System.out.println("时间自动转化：" + dateTest.toString());

        int daysNum = Period.between(startTime.toLocalDate(), endTime.toLocalDate()).getDays();
        int monthNum = Period.between(startTime.toLocalDate(), endTime.toLocalDate()).getMonths();
        System.out.println("相差天数：" + daysNum);
        System.out.println("相差月数：" + monthNum);

        System.out.println("当前时间向前推6天：" + LocalDateTime.now().minusDays(6));
        System.out.println("当前时间向前推6小时：" + LocalDateTime.now().minusHours(6));



        //一周从周日开始
        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 7);
        LocalDate today = LocalDate.now();
        System.out.println(LocalDateUtil.getDateAsString(today) + "今天是今年第" + today.get(weekFields.weekOfYear()) + "周");

        1、查询两个LocalDate的相差天数
        System.out.println(LocalDate.now().toEpochDay() - LocalDate.now().minusDays(5).toEpochDay());

        2、查询两个LocalDate的相差月数
        System.out.println(LocalDate.now().until(LocalDate.now().minusDays(5),ChronoUnit.MONTHS));

 */