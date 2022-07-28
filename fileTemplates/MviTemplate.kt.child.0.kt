#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

import ${PACKAGE_NAME}.entity.${NAME}Effect
import ${PACKAGE_NAME}.entity.${NAME}Event
import ru.subtlefox.mvi.flow.MviEventProducer

#parse("File Header.java")
object ${NAME}EventProducer : MviEventProducer<${NAME}Effect, ${NAME}Event> {
  
    override fun invoke( effect: ${NAME}Effect): ${NAME}Event? {
        return when (effect) {
            is ${NAME}Effect.Error -> {
                ${NAME}Event.ShowErrorDialog
            }
            
            else -> null
        }
    }
}
