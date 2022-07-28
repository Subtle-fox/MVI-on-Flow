#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.entity

#end

#parse("File Header.java")
data class ${NAME}State (
    val stub: Unit
) {
    companion object {
        val INITIAL =  ${NAME}State(Unit)
    }
}
