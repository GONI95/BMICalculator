package sang.gondroid.calingredientfood.domain.mapper

import sang.gondroid.calingredientfood.domain.util.ViewType

interface Mapper<I, O> {
    fun toModel(input: I, viewType: ViewType): O
    fun toEntity(input: O): I
}
