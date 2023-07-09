package ram.talia.moreiotas.common.casting.arithmetic.operator.string

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import ram.talia.moreiotas.api.asActionResult
import ram.talia.moreiotas.api.casting.iota.StringIota
import ram.talia.moreiotas.common.casting.arithmetic.operator.nextString
import ram.talia.moreiotas.common.lib.hex.MoreIotasIotaTypes.STRING
import kotlin.math.abs
import kotlin.math.roundToInt

object OperatorStringRemove : Operator(2, IotaMultiPredicate.pair(IotaPredicate.ofType(STRING), IotaPredicate.or(IotaPredicate.ofType(STRING), IotaPredicate.ofType(DOUBLE)))) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val removeFrom = it.nextString(arity)

        val (_, x) = it.next()
        return if (x is StringIota) {
            val toRemove = x.string
            removeFrom.replaceFirst(toRemove, "")
        } else {
            val double = (x as DoubleIota).double
            val rounded = double.roundToInt()
            if (abs(double - rounded) > DoubleIota.TOLERANCE || rounded !in 0..removeFrom.length) {
                throw MishapInvalidIota.of(x, 0, "int.positive.less.equal", removeFrom.length)
            }

            removeFrom.substring(0, rounded) + removeFrom.substring(rounded + 1)
        }.asActionResult
    }
}