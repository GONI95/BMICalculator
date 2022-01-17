package sang.gondroid.calingredientfood.domain.mapper

import sang.gondroid.calingredientfood.data.dto.network.NetworkItem
import sang.gondroid.calingredientfood.domain.model.ItemModel

class ToItemModelMapper : Mapper<NetworkItem, ItemModel> {
    override fun map(input: NetworkItem): ItemModel {
        return with(input) {
            ItemModel(company, beginYear, descriptionKOR, calorie, carbohydrate, protein, fat, sugar,
                salt, cholesterol, saturatedFattyAcid, transFat, sERVINGWT)
        }
    }
}