#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

import ${PACKAGE_NAME}.entity.${NAME}Action
import ${PACKAGE_NAME}.entity.${NAME}Effect
import ${PACKAGE_NAME}.entity.${NAME}Event
import ${PACKAGE_NAME}.entity.${NAME}State
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject

#parse("File Header.java")
class ${NAME}Feature @Inject constructor(
    bootstrap: ${NAME}Bootstrap,
    actor: ${NAME}Actor,
    eventProducer: ${NAME}EventProducer,
    reducer: ${NAME}Reducer,
) : MviFeature<${NAME}Action, ${NAME}Effect, ${NAME}State, ${NAME}Event>(
    initialState = ${NAME}State.INITIAL,
    bootstrap = bootstrap,
    actor = actor,
    eventProducer = eventProducer,
    reducer = reducer,
    tagPostfix = "${NAME}"
)