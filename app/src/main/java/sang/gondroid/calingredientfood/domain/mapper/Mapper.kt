package sang.gondroid.calingredientfood.domain.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
