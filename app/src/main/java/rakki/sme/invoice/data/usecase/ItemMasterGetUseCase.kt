package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.data.repository.ItemMasterEntryRepo
import javax.inject.Inject

class ItemMasterGetUseCase @Inject constructor(val itemMasterEntryRepo: ItemMasterEntryRepo) {

    suspend operator fun invoke(): List<ItemsMasterEntry> = withContext(Dispatchers.IO) {
        itemMasterEntryRepo.getAllBranch()
    }


}