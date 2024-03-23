// SPDX-License-Identifier: MIT
// Compatible with OpenZeppelin Contracts ^5.0.0
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract NectartToken is ERC721, Ownable {
    uint256 private _nextTokenId;

    constructor()
        ERC721("Nectart", "NECTART")
        Ownable(msg.sender)
    {}

    /* allow anyone to mint for testing purposes */
    function safeMint(address to) public {
        uint256 tokenId = _nextTokenId++;
        _safeMint(to, tokenId);
    }
}