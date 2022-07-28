#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

import ${PACKAGE_NAME}.entity.${NAME}Effect
import ${PACKAGE_NAME}.entity.${NAME}State
import ru.subtlefox.mvi.flow.MviReducer

${MVI_MODULE_NAME}

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
