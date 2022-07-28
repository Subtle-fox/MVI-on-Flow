#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.entity

#end

#parse("File Header.java")
sealed class ${NAME}Event {
    object ShowErrorDialog: ${NAME}Event()
}
