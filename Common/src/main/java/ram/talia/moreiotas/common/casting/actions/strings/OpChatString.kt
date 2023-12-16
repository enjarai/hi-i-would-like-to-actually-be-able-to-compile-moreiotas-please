package ram.talia.moreiotas.common.casting.actions.strings

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import ram.talia.moreiotas.api.asActionResult
import ram.talia.moreiotas.xplat.IXplatAbstractions

class OpChatString(private val allChat: Boolean) : ConstMediaAction {
    override val argc = 0

    override val mediaCost: Int
        get() = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return IXplatAbstractions.INSTANCE.lastMessage(if (allChat) null else env.caster)?.asActionResult ?:
            "".asActionResult
    }
}