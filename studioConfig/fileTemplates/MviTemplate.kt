#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

#parse("Mvi package.java")

import ${PACKAGE_NAME}.mvi.entity.${NAME}Action
import ${PACKAGE_NAME}.mvi.entity.${NAME}Effect
import ${PACKAGE_NAME}.mvi.entity.${NAME}Event
import ${PACKAGE_NAME}.mvi.entity.${NAME}State
import ${MVI_PACKAGE}.MviFeature
import javax.inject.Inject

#parse("File Header.java")
class ${NAME}Feature @Inject constructor(
    bootstrap: ${NAME}Bootstrap,
    actor: ${NAME}Actor,
) : MviFeature<${NAME}Action, ${NAME}Effect, ${NAME}State, ${NAME}Event>(
    initialState = ${NAME}State.INITIAL,
    bootstrap = bootstrap,
    actor = actor,
    eventProducer = ${NAME}EventProducer,
    reducer = ${NAME}Reducer,
    tagPostfix = "${NAME}"
)