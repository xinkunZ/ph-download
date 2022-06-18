import org.apache.commons.lang3.StringUtils
import java.io.File

/**
 * @author zhangxinkun
 */
class Test

fun main() {
    val sb = StringBuilder()
    File("/users/zhangxinkun/downloads/pornhub.com_cookies.txt").forEachLine {
        if (it.startsWith("#") || StringUtils.isBlank(it)) {
            return@forEachLine
        }
        val split = StringUtils.split(it, '\t')
        if (split.size == 7) {
            sb.append(split[5] + "=" + split[6] + ";")
        }
    }
    println(sb.toString())
}