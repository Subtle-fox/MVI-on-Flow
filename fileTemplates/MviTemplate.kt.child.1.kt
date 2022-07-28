#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ${PACKAGE_NAME}.entity.${NAME}Action
import ${PACKAGE_NAME}.entity.${NAME}Effect
import ${PACKAGE_NAME}.entity.${NAME}State
import ru.subtlefox.mvi.flow.MviActor
import javax.inject.Inject

#parse("File Header.java")
class ${NAME}Actor @Inject constructor(

) : MviActor<${NAME}Action, ${NAME}Effect, ${NAME}State> {

    override fun invoke(
        action: ${NAME}Action,
        previousState: ${NAME}State
    ): Flow<${NAME}Effect> {
        return when (action) {
            is ${NAME}Action.Stub -> flow<XEffect> {
                // Paste business logic here
                emit(XEffect.Stub)
            }
        }.catch {
            emit(XEffect.Error(it))
        }
    }
}