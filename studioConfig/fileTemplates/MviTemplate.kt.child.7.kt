#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

#parse("Mvi package.java")

import ${PACKAGE_NAME}.mvi.entity.${NAME}Effect
import ${PACKAGE_NAME}.mvi.entity.${NAME}State
import ${MVI_PACKAGE}.MviReducer

#parse("File Header.java")
object ${NAME}Reducer : MviReducer<${NAME}Effect, ${NAME}State> {
    override suspend fun invoke(
        effect: ${NAME}Effect, 
        previousState: ${NAME}State
    ): ${NAME}State = when (effect) {
        
        is ${NAME}Effect.Stub -> {
            // Modify previous state by incoming ${NAME}Effect
            previousState
        }
        
        else -> previousState
    }
}
