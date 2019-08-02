package co.lithobyte.recycleradapter.models

interface OnBoundProvider {
    var onBound: ((Int, Int) -> Unit)?
}