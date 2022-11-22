from dataclasses import dataclass
from typing import Protocol


class ILoString(Protocol):
    def count(self) -> int:
        ...


class ConsLoString(ILoString):
    def __init__(self, first: str, rest: ILoString) -> None:
        self.first = first
        self.rest = rest

    def count(self) -> int:
        return 1 + self.rest.count()


class MtLoString(ILoString):
    def count(self) -> int:
        return 0


class IAT(Protocol):

    # To compute the number of known ancestors of this ancestor tree(excluding this ancestor tree itself)
    def count(self) -> int:
        ...

    def count_helper(self) -> int:
        ...

    # To compute how many ancestors of this ancestor tree (excluding this ancestor tree itself) are women older than 40
    # (in the current year)?
    def female_anc_over_40(self) -> int:
        ...

    def female_anc_over_40_helper(self) -> int:
        ...

    # To compute whether this ancestor tree is well-formed: are all known people younger than their parents?
    def well_formed(self) -> bool:
        ...

    # To determine if this ancestry tree is born in or before the given year
    def born_in_or_before(self, yob: int) -> bool:
        ...

    # To compute the names of all the known ancestors in this ancestor tree (including this ancestor tree itself)
    def names(self) -> ILoString:
        ...

    # To compute this ancestor tree's youngest grandparent
    def youngest_grandparent(self) -> str:
        ...


class Unknown(IAT):
    def count(self) -> int:
        return 0

    def count_helper(self) -> int:
        return 0

    def female_anc_over_40(self) -> int:
        return 0

    def female_anc_over_40_helper(self) -> int:
        return 0

    def well_formed(self) -> bool:
        return True

    def born_in_or_before(self, yob: int) -> bool:
        return True

    def names(self) -> ILoString:
        return MtLoString()

    def youngest_grandparent(self) -> str:
        raise ValueError("No known ancestors")


@dataclass
class Person(IAT):
    name: str
    yob: int
    is_male: bool
    mom: IAT
    dad: IAT

    def count(self) -> int:
        return self.mom.count_helper() + self.dad.count_helper()

    def count_helper(self):
        return 1 + self.mom.count_helper() + self.dad.count()

    def female_anc_over_40(self) -> int:
        """
        To compute how many ancestors of this ancestor tree (excluding this ancestor tree itself)
        are women older than 40
        :return: int
        """
        return self.female_anc_over_40_helper()

    def female_anc_over_40_helper(self):
        # todo: show how to refactor this method
        if (2022 - self.yob) > 40 and not self.is_male:
            return 1 + self.mom.female_anc_over_40_helper() + self.dad.female_anc_over_40_helper()
        else:
            return self.mom.female_anc_over_40_helper() + self.dad.female_anc_over_40_helper()

    def well_formed(self) -> bool:
        """
        To compute whether this ancestor tree is well-formed: are all known people younger than their parents?
        :return: bool
        """
        def am_i_well_formed() -> bool:
            return self.mom.born_in_or_before(self.yob) and self.dad.born_in_or_before(self.yob)

        def are_parents_well_formed() -> bool:
            return self.mom.well_formed() and self.dad.well_formed()

        return am_i_well_formed() and are_parents_well_formed()

    def born_in_or_before(self, yob: int) -> bool:
        """
        To determine if this ancestry tree is born in or before the given year
        :param yob: int
        :return: bool
        """
        return self.yob <= yob
