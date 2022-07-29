#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

#parse("Mvi package.java")

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ${PACKAGE_NAME}.mvi.entity.${NAME}Effect
import ${MVI_PACKAGE}.MviBootstrap
import javax.inject.Inject

#parse("File Header.java")
class ${NAME}Bootstrap @Inject constructor(

) : MviBootstrap<${NAME}Effect> {

    override fun invoke(): Flow<${NAME}Effect> {
        return flow<${NAME}Effect> {
            // Paste business logic here
            emit(${NAME}Effect.Stub)
        }.catch {
            emit(${NAME}Effect.Error(it))
        }
    }
}