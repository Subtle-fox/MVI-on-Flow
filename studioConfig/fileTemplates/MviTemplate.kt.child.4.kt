#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi.entity

#end

#parse("File Header.java")
sealed class ${NAME}Effect {
    object Stub: ${NAME}Effect()
    data class Error(val error: Throwable): ${NAME}Effect()
}
