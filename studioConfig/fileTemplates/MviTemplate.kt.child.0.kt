#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}.mvi

#end

#parse("Mvi package.java")

import ${PACKAGE_NAME}.mvi.entity.${NAME}Effect
import ${PACKAGE_NAME}.mvi.entity.${NAME}Event
import ${MVI_PACKAGE}.MviEventProducer

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
