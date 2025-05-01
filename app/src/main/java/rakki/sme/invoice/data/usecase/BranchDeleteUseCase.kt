package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.dao.BranchDao
import rakki.sme.invoice.data.model.Profile
import javax.inject.Inject

class BranchDeleteUseCase @Inject constructor(val branchDao: BranchDao) {
    suspend operator fun invoke(item: Profile): Flow<Boolean> =
        withContext(Dispatchers.IO) {
            branchDao.delete(item)
            channelFlow { send(true) }
        }
}