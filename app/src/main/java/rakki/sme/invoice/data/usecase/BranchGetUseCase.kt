package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.repository.BranchRepo
import javax.inject.Inject

class BranchGetUseCase @Inject constructor(val branchRepo: BranchRepo) {

    suspend operator fun invoke(): List<Profile> = withContext(Dispatchers.IO) {
        branchRepo.getAllBranch()
    }


}