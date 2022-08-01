#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

#parse("Mvi package.java")

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ${PACKAGE_NAME}.mvi.entity.${NAME}Action
import ${PACKAGE_NAME}.mvi.entity.${NAME}Effect
import ${PACKAGE_NAME}.mvi.entity.${NAME}State
import ${MVI_PACKAGE}.MviActor
import javax.inject.Inject

#parse("File Header.java")
class ${NAME}Actor @Inject constructor(

) : MviActor<${NAME}Action, ${NAME}Effect, ${NAME}State> {

    override fun invoke(
        action: ${NAME}Action,
        previousState: ${NAME}State
    ): Flow<${NAME}Effect> {
        return when (action) {
            is ${NAME}Action.Stub -> flow<${NAME}Effect> {
                // Paste business logic here
                emit(${NAME}Effect.Stub)
            }
        }.catch {
            emit(${NAME}Effect.Error(it))
        }
    }
}