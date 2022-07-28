#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ${PACKAGE_NAME}.entity.${NAME}Effect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

#parse("File Header.java")
class ${NAME}Bootstrap @Inject constructor(

) : MviBootstrap<${NAME}Effect> {

    override fun invoke(): Flow<${NAME}Effect> {
        return flow<XEffect> {
            // Paste business logic here
            emit(XEffect.Stub)
        }.catch {
            emit(XEffect.Error(it))
        }
    }
}