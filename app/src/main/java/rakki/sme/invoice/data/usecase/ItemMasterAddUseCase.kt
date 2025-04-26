package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.data.repository.ItemMasterEntryRepo
import javax.inject.Inject

class ItemMasterAddUseCase @Inject constructor(val itemMasterEntryRepo: ItemMasterEntryRepo) {
    suspend operator fun invoke(itemMasterEntry: ItemsMasterEntry): Flow<Boolean> =
        withContext(Dispatchers.IO) {
            itemMasterEntryRepo.saveMasterItemEntry(itemMasterEntry)
            channelFlow { send(true) }
        }
}