#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi.entity

#end

#parse("File Header.java")
sealed class ${NAME}Action {
    object Stub: ${NAME}Action()
}
